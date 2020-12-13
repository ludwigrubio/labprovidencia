import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { LogLactoEscanUpdateComponent } from 'app/entities/log-lacto-escan/log-lacto-escan-update.component';
import { LogLactoEscanService } from 'app/entities/log-lacto-escan/log-lacto-escan.service';
import { LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

describe('Component Tests', () => {
  describe('LogLactoEscan Management Update Component', () => {
    let comp: LogLactoEscanUpdateComponent;
    let fixture: ComponentFixture<LogLactoEscanUpdateComponent>;
    let service: LogLactoEscanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [LogLactoEscanUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LogLactoEscanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LogLactoEscanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LogLactoEscanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LogLactoEscan(123);
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
        const entity = new LogLactoEscan();
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
