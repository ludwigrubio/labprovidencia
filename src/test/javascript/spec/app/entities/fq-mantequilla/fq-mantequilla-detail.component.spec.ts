import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQMantequillaDetailComponent } from 'app/entities/fq-mantequilla/fq-mantequilla-detail.component';
import { FQMantequilla } from 'app/shared/model/fq-mantequilla.model';

describe('Component Tests', () => {
  describe('FQMantequilla Management Detail Component', () => {
    let comp: FQMantequillaDetailComponent;
    let fixture: ComponentFixture<FQMantequillaDetailComponent>;
    const route = ({ data: of({ fQMantequilla: new FQMantequilla(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQMantequillaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FQMantequillaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FQMantequillaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fQMantequilla on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fQMantequilla).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
