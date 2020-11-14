import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FQLecheService } from 'app/entities/fq-leche/fq-leche.service';
import { IFQLeche, FQLeche } from 'app/shared/model/fq-leche.model';

describe('Service Tests', () => {
  describe('FQLeche Service', () => {
    let injector: TestBed;
    let service: FQLecheService;
    let httpMock: HttpTestingController;
    let elemDefault: IFQLeche;
    let expectedResult: IFQLeche | IFQLeche[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FQLecheService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FQLeche(
        0,
        currentDate,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FQLeche', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.create(new FQLeche()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FQLeche', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            acidez: 1,
            temperatura: 1,
            agua: 1,
            crioscopia: 1,
            antibiotico: 1,
            delvo: 1,
            grasa: 1,
            solidos: 'BBBBBB',
            densidad: 1,
            lactosa: 1,
            proteina: 1,
            neutralizantes: 1,
            adulterantes: 1,
            reductasa: 1,
            fosfatasa: 1,
            ph: 1,
            dummy1: 1,
            dummy2: 1,
            dummy3: 1,
            dummy4: 1,
            dummy5: 1,
            observaciones: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FQLeche', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            acidez: 1,
            temperatura: 1,
            agua: 1,
            crioscopia: 1,
            antibiotico: 1,
            delvo: 1,
            grasa: 1,
            solidos: 'BBBBBB',
            densidad: 1,
            lactosa: 1,
            proteina: 1,
            neutralizantes: 1,
            adulterantes: 1,
            reductasa: 1,
            fosfatasa: 1,
            ph: 1,
            dummy1: 1,
            dummy2: 1,
            dummy3: 1,
            dummy4: 1,
            dummy5: 1,
            observaciones: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FQLeche', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
