import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FQMantequillaService } from 'app/entities/fq-mantequilla/fq-mantequilla.service';
import { IFQMantequilla, FQMantequilla } from 'app/shared/model/fq-mantequilla.model';

describe('Service Tests', () => {
  describe('FQMantequilla Service', () => {
    let injector: TestBed;
    let service: FQMantequillaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFQMantequilla;
    let expectedResult: IFQMantequilla | IFQMantequilla[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FQMantequillaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FQMantequilla(0, currentDate, 'AAAAAAA', 0, 0, 'AAAAAAA');
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

      it('should create a FQMantequilla', () => {
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

        service.create(new FQMantequilla()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FQMantequilla', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            ph: 1,
            humedad: 1,
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

      it('should return a list of FQMantequilla', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            lote: 'BBBBBB',
            ph: 1,
            humedad: 1,
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

      it('should delete a FQMantequilla', () => {
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
