import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQQuesoUpdateComponent } from 'app/entities/fq-queso/fq-queso-update.component';
import { FQQuesoService } from 'app/entities/fq-queso/fq-queso.service';
import { FQQueso } from 'app/shared/model/fq-queso.model';

describe('Component Tests', () => {
  describe('FQQueso Management Update Component', () => {
    let comp: FQQuesoUpdateComponent;
    let fixture: ComponentFixture<FQQuesoUpdateComponent>;
    let service: FQQuesoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQQuesoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FQQuesoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQQuesoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQQuesoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQQueso(123);
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
        const entity = new FQQueso();
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
