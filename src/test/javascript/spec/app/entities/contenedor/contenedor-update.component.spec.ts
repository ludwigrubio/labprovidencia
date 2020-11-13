import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { ContenedorUpdateComponent } from 'app/entities/contenedor/contenedor-update.component';
import { ContenedorService } from 'app/entities/contenedor/contenedor.service';
import { Contenedor } from 'app/shared/model/contenedor.model';

describe('Component Tests', () => {
  describe('Contenedor Management Update Component', () => {
    let comp: ContenedorUpdateComponent;
    let fixture: ComponentFixture<ContenedorUpdateComponent>;
    let service: ContenedorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [ContenedorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContenedorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContenedorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContenedorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contenedor(123);
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
        const entity = new Contenedor();
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
