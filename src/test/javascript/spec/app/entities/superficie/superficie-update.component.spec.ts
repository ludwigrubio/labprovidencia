import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { SuperficieUpdateComponent } from 'app/entities/superficie/superficie-update.component';
import { SuperficieService } from 'app/entities/superficie/superficie.service';
import { Superficie } from 'app/shared/model/superficie.model';

describe('Component Tests', () => {
  describe('Superficie Management Update Component', () => {
    let comp: SuperficieUpdateComponent;
    let fixture: ComponentFixture<SuperficieUpdateComponent>;
    let service: SuperficieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [SuperficieUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SuperficieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuperficieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuperficieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Superficie(123);
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
        const entity = new Superficie();
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
