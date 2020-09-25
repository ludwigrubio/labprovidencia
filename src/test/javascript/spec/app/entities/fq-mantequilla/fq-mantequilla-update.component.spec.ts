import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQMantequillaUpdateComponent } from 'app/entities/fq-mantequilla/fq-mantequilla-update.component';
import { FQMantequillaService } from 'app/entities/fq-mantequilla/fq-mantequilla.service';
import { FQMantequilla } from 'app/shared/model/fq-mantequilla.model';

describe('Component Tests', () => {
  describe('FQMantequilla Management Update Component', () => {
    let comp: FQMantequillaUpdateComponent;
    let fixture: ComponentFixture<FQMantequillaUpdateComponent>;
    let service: FQMantequillaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQMantequillaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FQMantequillaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FQMantequillaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FQMantequillaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FQMantequilla(123);
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
        const entity = new FQMantequilla();
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
