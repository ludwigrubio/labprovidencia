import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQLecheUpdateComponent } from 'app/entities/fq-leche/fq-leche-update.component';
import { FQLecheService } from 'app/entities/fq-leche/fq-leche.service';
import { FQLeche } from 'app/shared/model/fq-leche.model';

describe('Component Tests', () => {
  describe('FQLeche Management Update Component', () => {
    let comp: FQLecheUpdateComponent;
    let fixture: ComponentFixture<FQLecheUpdateComponent>;
    let service: FQLecheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQLecheUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FQLecheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQLecheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQLecheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQLeche(123);
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
        const entity = new FQLeche();
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
