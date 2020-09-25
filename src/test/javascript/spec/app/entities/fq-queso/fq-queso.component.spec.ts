import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQQuesoComponent } from 'app/entities/fq-queso/fq-queso.component';
import { FQQuesoService } from 'app/entities/fq-queso/fq-queso.service';
import { FQQueso } from 'app/shared/model/fq-queso.model';

describe('Component Tests', () => {
  describe('FQQueso Management Component', () => {
    let comp: FQQuesoComponent;
    let fixture: ComponentFixture<FQQuesoComponent>;
    let service: FQQuesoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQQuesoComponent],
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
        .overrideTemplate(FQQuesoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQQuesoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQQuesoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FQQueso(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fQQuesos && comp.fQQuesos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FQQueso(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fQQuesos && comp.fQQuesos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
