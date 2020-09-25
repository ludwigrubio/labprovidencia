import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { RecepcionUpdateComponent } from 'app/entities/recepcion/recepcion-update.component';
import { RecepcionService } from 'app/entities/recepcion/recepcion.service';
import { Recepcion } from 'app/shared/model/recepcion.model';

describe('Component Tests', () => {
  describe('Recepcion Management Update Component', () => {
    let comp: RecepcionUpdateComponent;
    let fixture: ComponentFixture<RecepcionUpdateComponent>;
    let service: RecepcionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [RecepcionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RecepcionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecepcionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecepcionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recepcion(123);
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
        const entity = new Recepcion();
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
