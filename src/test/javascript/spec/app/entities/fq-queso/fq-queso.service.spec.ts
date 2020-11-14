import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FQQuesoService } from 'app/entities/fq-queso/fq-queso.service';
import { IFQQueso, FQQueso } from 'app/shared/model/fq-queso.model';

describe('Service Tests', () => {
  describe('FQQueso Service', () => {
    let injector: TestBed;
    let service: FQQuesoService;
    let httpMock: HttpTestingController;
    let elemDefault: IFQQueso;
    let expectedResult: IFQQueso | IFQQueso[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FQQuesoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FQQueso(0, currentDate, 'AAAAAAA', 0, 0, 0, 0, currentDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            caducidad: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FQQueso', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT),
            caducidad: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            caducidad: currentDate,
          },
          returnedFromService
        );

        service.create(new FQQueso()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FQQueso', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            humedad: 1,
            ph: 1,
            fundicion: 1,
            presentacion: 1,
            caducidad: currentDate.format(DATE_TIME_FORMAT),
            apariencia: 1,
            sabor: 1,
            color: 1,
            olor: 1,
            textura: 1,
            hilado: 1,
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
            caducidad: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FQQueso', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            humedad: 1,
            ph: 1,
            fundicion: 1,
            presentacion: 1,
            caducidad: currentDate.format(DATE_TIME_FORMAT),
            apariencia: 1,
            sabor: 1,
            color: 1,
            olor: 1,
            textura: 1,
            hilado: 1,
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
            caducidad: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FQQueso', () => {
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
