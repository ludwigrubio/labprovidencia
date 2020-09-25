import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PruebaMicroService } from 'app/entities/prueba-micro/prueba-micro.service';
import { IPruebaMicro, PruebaMicro } from 'app/shared/model/prueba-micro.model';

describe('Service Tests', () => {
  describe('PruebaMicro Service', () => {
    let injector: TestBed;
    let service: PruebaMicroService;
    let httpMock: HttpTestingController;
    let elemDefault: IPruebaMicro;
    let expectedResult: IPruebaMicro | IPruebaMicro[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PruebaMicroService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PruebaMicro(0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PruebaMicro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.create(new PruebaMicro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PruebaMicro', () => {
        const returnedFromService = Object.assign(
          {
            tipodeMuestra: 1,
            idCatalogo: 'BBBBBB',
            lote: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            resultado: 1,
            unidades: 1,
            observaciones: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PruebaMicro', () => {
        const returnedFromService = Object.assign(
          {
            tipodeMuestra: 1,
            idCatalogo: 'BBBBBB',
            lote: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            resultado: 1,
            unidades: 1,
            observaciones: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicio: currentDate,
            fin: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PruebaMicro', () => {
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
