import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LogLactoEscanService } from 'app/entities/log-lacto-escan/log-lacto-escan.service';
import { ILogLactoEscan, LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

describe('Service Tests', () => {
  describe('LogLactoEscan Service', () => {
    let injector: TestBed;
    let service: LogLactoEscanService;
    let httpMock: HttpTestingController;
    let elemDefault: ILogLactoEscan;
    let expectedResult: ILogLactoEscan | ILogLactoEscan[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LogLactoEscanService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LogLactoEscan(0, 0, currentDate, 'AAAAAAA', 0, 'AAAAAAA');
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

      it('should create a LogLactoEscan', () => {
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

        service.create(new LogLactoEscan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LogLactoEscan', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 1,
            fecha: currentDate.format(DATE_TIME_FORMAT),
            nombreArchivo: 'BBBBBB',
            numeroFila: 1,
            mensajeError: 'BBBBBB',
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

      it('should return a list of LogLactoEscan', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 1,
            fecha: currentDate.format(DATE_TIME_FORMAT),
            nombreArchivo: 'BBBBBB',
            numeroFila: 1,
            mensajeError: 'BBBBBB',
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

      it('should delete a LogLactoEscan', () => {
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
