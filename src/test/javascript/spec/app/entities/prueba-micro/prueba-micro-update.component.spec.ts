import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { PruebaMicroUpdateComponent } from 'app/entities/prueba-micro/prueba-micro-update.component';
import { PruebaMicroService } from 'app/entities/prueba-micro/prueba-micro.service';
import { PruebaMicro } from 'app/shared/model/prueba-micro.model';

describe('Component Tests', () => {
  describe('PruebaMicro Management Update Component', () => {
    let comp: PruebaMicroUpdateComponent;
    let fixture: ComponentFixture<PruebaMicroUpdateComponent>;
    let service: PruebaMicroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [PruebaMicroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PruebaMicroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PruebaMicroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PruebaMicroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PruebaMicro(123);
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
        const entity = new PruebaMicro();
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
