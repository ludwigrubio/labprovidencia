import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RecepcionService } from 'app/entities/recepcion/recepcion.service';
import { IRecepcion, Recepcion } from 'app/shared/model/recepcion.model';

describe('Service Tests', () => {
  describe('Recepcion Service', () => {
    let injector: TestBed;
    let service: RecepcionService;
    let httpMock: HttpTestingController;
    let elemDefault: IRecepcion;
    let expectedResult: IRecepcion | IRecepcion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RecepcionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Recepcion(0, 0, 0, currentDate, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            tiempo: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Recepcion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            tiempo: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tiempo: currentDate,
          },
          returnedFromService
        );

        service.create(new Recepcion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Recepcion', () => {
        const returnedFromService = Object.assign(
          {
            idProveedor: 1,
            litros: 1,
            tiempo: currentDate.format(DATE_TIME_FORMAT),
            turno: 'BBBBBB',
            incentivoLT: 1,
            incentivoT: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tiempo: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Recepcion', () => {
        const returnedFromService = Object.assign(
          {
            idProveedor: 1,
            litros: 1,
            tiempo: currentDate.format(DATE_TIME_FORMAT),
            turno: 'BBBBBB',
            incentivoLT: 1,
            incentivoT: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tiempo: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Recepcion', () => {
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
