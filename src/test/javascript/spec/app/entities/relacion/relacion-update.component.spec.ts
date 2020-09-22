import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { RelacionUpdateComponent } from 'app/entities/relacion/relacion-update.component';
import { RelacionService } from 'app/entities/relacion/relacion.service';
import { Relacion } from 'app/shared/model/relacion.model';

describe('Component Tests', () => {
  describe('Relacion Management Update Component', () => {
    let comp: RelacionUpdateComponent;
    let fixture: ComponentFixture<RelacionUpdateComponent>;
    let service: RelacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [RelacionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RelacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Relacion(123);
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
        const entity = new Relacion();
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
