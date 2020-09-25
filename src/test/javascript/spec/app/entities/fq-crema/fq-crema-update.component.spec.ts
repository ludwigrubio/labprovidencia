import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQCremaUpdateComponent } from 'app/entities/fq-crema/fq-crema-update.component';
import { FQCremaService } from 'app/entities/fq-crema/fq-crema.service';
import { FQCrema } from 'app/shared/model/fq-crema.model';

describe('Component Tests', () => {
  describe('FQCrema Management Update Component', () => {
    let comp: FQCremaUpdateComponent;
    let fixture: ComponentFixture<FQCremaUpdateComponent>;
    let service: FQCremaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQCremaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FQCremaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQCremaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQCremaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQCrema(123);
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
        const entity = new FQCrema();
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
