import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PersonalService } from 'app/entities/personal/personal.service';
import { IPersonal, Personal } from 'app/shared/model/personal.model';

describe('Service Tests', () => {
  describe('Personal Service', () => {
    let injector: TestBed;
    let service: PersonalService;
    let httpMock: HttpTestingController;
    let elemDefault: IPersonal;
    let expectedResult: IPersonal | IPersonal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PersonalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Personal(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a Personal', () => {
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

        service.create(new Personal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Personal', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            apellido1: 'BBBBBB',
            apellido2: 'BBBBBB',
            alias: 'BBBBBB',
            domicilio: 'BBBBBB',
            colonia: 'BBBBBB',
            localidad: 'BBBBBB',
            estado: 'BBBBBB',
            pais: 'BBBBBB',
            latitud: 'BBBBBB',
            longitud: 'BBBBBB',
            cp: 1,
            telefono: 'BBBBBB',
            email: 'BBBBBB',
            rfc: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            cargo: 'BBBBBB',
            comentario: 'BBBBBB',
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

      it('should return a list of Personal', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            apellido1: 'BBBBBB',
            apellido2: 'BBBBBB',
            alias: 'BBBBBB',
            domicilio: 'BBBBBB',
            colonia: 'BBBBBB',
            localidad: 'BBBBBB',
            estado: 'BBBBBB',
            pais: 'BBBBBB',
            latitud: 'BBBBBB',
            longitud: 'BBBBBB',
            cp: 1,
            telefono: 'BBBBBB',
            email: 'BBBBBB',
            rfc: 'BBBBBB',
            inicio: currentDate.format(DATE_TIME_FORMAT),
            fin: currentDate.format(DATE_TIME_FORMAT),
            cargo: 'BBBBBB',
            comentario: 'BBBBBB',
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

      it('should delete a Personal', () => {
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
