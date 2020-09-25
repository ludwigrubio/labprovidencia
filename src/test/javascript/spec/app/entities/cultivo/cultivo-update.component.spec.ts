import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { CultivoUpdateComponent } from 'app/entities/cultivo/cultivo-update.component';
import { CultivoService } from 'app/entities/cultivo/cultivo.service';
import { Cultivo } from 'app/shared/model/cultivo.model';

describe('Component Tests', () => {
  describe('Cultivo Management Update Component', () => {
    let comp: CultivoUpdateComponent;
    let fixture: ComponentFixture<CultivoUpdateComponent>;
    let service: CultivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [CultivoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CultivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CultivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CultivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cultivo(123);
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
        const entity = new Cultivo();
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
