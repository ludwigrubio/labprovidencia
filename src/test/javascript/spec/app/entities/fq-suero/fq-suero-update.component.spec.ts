import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQSueroUpdateComponent } from 'app/entities/fq-suero/fq-suero-update.component';
import { FQSueroService } from 'app/entities/fq-suero/fq-suero.service';
import { FQSuero } from 'app/shared/model/fq-suero.model';

describe('Component Tests', () => {
  describe('FQSuero Management Update Component', () => {
    let comp: FQSueroUpdateComponent;
    let fixture: ComponentFixture<FQSueroUpdateComponent>;
    let service: FQSueroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQSueroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FQSueroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQSueroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQSueroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQSuero(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQSuero();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
