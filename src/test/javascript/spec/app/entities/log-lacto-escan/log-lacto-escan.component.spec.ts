import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { LabprovidenciaTestModule } from '../../../test.module';
import { LogLactoEscanComponent } from 'app/entities/log-lacto-escan/log-lacto-escan.component';
import { LogLactoEscanService } from 'app/entities/log-lacto-escan/log-lacto-escan.service';
import { LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

describe('Component Tests', () => {
  describe('LogLactoEscan Management Component', () => {
    let comp: LogLactoEscanComponent;
    let fixture: ComponentFixture<LogLactoEscanComponent>;
    let service: LogLactoEscanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [LogLactoEscanComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(LogLactoEscanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogLactoEscanComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogLactoEscanService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogLactoEscan(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logLactoEscans && comp.logLactoEscans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LogLactoEscan(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.logLactoEscans && comp.logLactoEscans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
