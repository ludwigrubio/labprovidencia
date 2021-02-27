package com.provi.lab.service;

import com.provi.lab.domain.LogLactoEscan;
import com.provi.lab.domain.FQLeche;
import com.provi.lab.repository.FQLecheRepository;
import com.provi.lab.repository.LogLactoEscanRepository;
import com.provi.lab.service.dto.FQLecheCriteria;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.opencsv.CSVReader;

/**
 * Service Implementation for managing {@link LogLactoEscan}.
 */
@Service
@Transactional
public class LogLactoEscanService {

    private final Logger log = LoggerFactory.getLogger(LogLactoEscanService.class);

    private final LogLactoEscanRepository logLactoEscanRepository;

    private final FQLecheQueryService fQLecheQueryService;

    private final FQLecheRepository fqLecheRepository;

    @Value("${application.lactoescan-toread}")
    private String lactoescanToread;

    @Value("${application.lactoescan-readed}")
    private String lactoescanReaded;

    public LogLactoEscanService(LogLactoEscanRepository logLactoEscanRepository,
                                FQLecheQueryService fQLecheQueryService,
                                FQLecheRepository fqLecheRepository) {
        this.logLactoEscanRepository = logLactoEscanRepository;
        this.fQLecheQueryService = fQLecheQueryService;
        this.fqLecheRepository = fqLecheRepository;
    }

    /**
     * Save a logLactoEscan.
     *
     * @param logLactoEscan the entity to save.
     * @return the persisted entity.
     */
    public LogLactoEscan save(LogLactoEscan logLactoEscan) {
        log.debug("Request to save LogLactoEscan : {}", logLactoEscan);
        return logLactoEscanRepository.save(logLactoEscan);
    }

    /**
     * Get all the logLactoEscans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogLactoEscan> findAll(Pageable pageable) {
        log.debug("Request to get all LogLactoEscans");
        return logLactoEscanRepository.findAll(pageable);
    }


    /**
     * Get one logLactoEscan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogLactoEscan> findOne(Long id) {
        log.debug("Request to get LogLactoEscan : {}", id);
        return logLactoEscanRepository.findById(id);
    }

    /**
     * Delete the logLactoEscan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LogLactoEscan : {}", id);
        logLactoEscanRepository.deleteById(id);
    }

    /**
     * Read Folder every minute trying to fin new files
     *
     */
    @Scheduled(cron = "0 * * * * *")
    public void readCSVInFolder() {

        File f = new File(this.lactoescanToread);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".csv");
            }
        };
        File[] files = f.listFiles(filter);

        //Moving to temporal folder while being processed
        for (int i = 0; i < files.length; i++) {
            log.debug(new File(this.lactoescanToread + "\\Processing\\" + files[i].getName()).toPath().toString());
            try {
                Files.move(new File(this.lactoescanToread + "\\" +  files[i].getName()).toPath(),
                            new File(this.lactoescanToread + "\\Processing\\" + files[i].getName()).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                log.debug(ex.getMessage());
            }
        }


        //Processing CSV File
        for (int i = 0; i < files.length; i++) {

            ArrayList fqlechesArray = new ArrayList<FQLeche>();
            Boolean anyError = false;
            String fileName = this.lactoescanToread + "\\Processing\\" + files[i].getName();

            try{
                Reader reader = Files.newBufferedReader((Paths.get(fileName)), StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReader(reader);
                // Reading Records One by One in a String array
                String[] nextRecord;
                Integer numberRow =0;

                while ((nextRecord = csvReader.readNext()) != null) {
                    if(nextRecord.length != 13){
                        this.createNewLog(2, fileName, numberRow,
                            "La fila contiene más o menos columnas de las esperadas.");
                        anyError = true;
                    }

                    FQLecheCriteria fqc = new FQLecheCriteria();
                        //Filter fecha
                        InstantFilter inst = new InstantFilter();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
                        String date = nextRecord[0].replaceAll("[^a-zA-Z0-9]", "");
                        Instant t1 = LocalDate.parse(date, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant();
                        Instant t2 = LocalDate.parse(date, formatter).atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant();
                        inst.setGreaterThanOrEqual(t1);
                        inst.setLessThan(t2);
                        //Filter proveedor
                        LongFilter pid = new LongFilter();
                        pid.setEquals(new Long(nextRecord[2]));
                    fqc.setFecha(inst);
                    fqc.setProveedorId(pid);
                    List<FQLeche> fq  = fQLecheQueryService.findByCriteria(fqc);

                    switch (fq.size()){
                        case 0:
                            this.createNewLog(2, fileName, numberRow,
                                String.format("No se encontró un FQLeche creado para la fecha %s y el proovedor con identificador %s", nextRecord[0], nextRecord[2]));
                            anyError = true;
                        break;
                        case 1:
                            //Actualizar element encontrado

                            FQLeche lactoScan = fq.get(0);
                            lactoScan.setTemperatura(Double.parseDouble(nextRecord[6]));
                            lactoScan.setGrasa(Double.parseDouble(nextRecord[7]));
                            lactoScan.setSolidos(nextRecord[8]);
                            lactoScan.setDensidad(Double.parseDouble(nextRecord[9]));
                            lactoScan.setProteina(Double.parseDouble(nextRecord[10]));
                            lactoScan.setLactosa(Double.parseDouble(nextRecord[11]));
                            lactoScan.setAgua(Double.parseDouble(nextRecord[12]));

                            fqlechesArray.add(lactoScan);

                        break;
                        default:
                            this.createNewLog(2, fileName, numberRow,
                                String.format("Se encontró más de un FQLeche creado para la fecha %s y el proovedor con identificador %s", nextRecord[0], nextRecord[2]));
                            anyError = true;
                        break;
                    }
                    numberRow = numberRow++;
                }
            }catch(Exception ioe){
                log.error(ioe.getMessage());
                this.createNewLog(1, fileName, -1,
                    "Archivo mal formateado, no es posible leer como csv");
                anyError=true;
                try {
                    Files.move(new File(this.lactoescanToread + "\\Processing\\" +  files[i].getName()).toPath(),
                        new File(this.lactoescanToread + "\\Error\\" + files[i].getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    log.debug(ex.toString());
                }
            }

            if(!anyError){
                fqlechesArray.forEach(fql -> {
                    fqLecheRepository.save((FQLeche) fql);
                });
                // Move file to read if completed
                try {
                    Files.move(new File(this.lactoescanToread + "\\Processing\\" +  files[i].getName()).toPath(),
                        new File(this.lactoescanToread + "\\Read\\" + files[i].getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    log.debug(ex.getMessage());
                }
            }else{
                try {
                    Files.move(new File(this.lactoescanToread + "\\Processing\\" +  files[i].getName()).toPath(),
                        new File(this.lactoescanToread + "\\Error\\" + files[i].getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    log.debug(ex.getMessage());
                }
            }


        }
    }

    public void createNewLog(Integer tipo, String nombre_archivo, Integer numero_fila, String mensaje){

        LogLactoEscan la = new LogLactoEscan();
        la.setTipo(tipo);
        la.setFecha(ZonedDateTime.now(ZoneId.systemDefault()).toInstant());
        la.setMensajeError(mensaje);
        la.setNombreArchivo(nombre_archivo);
        la.setNumeroFila(numero_fila);
        logLactoEscanRepository.save(la);
    }
}
