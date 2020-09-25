import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQLecheDetailComponent } from 'app/entities/fq-leche/fq-leche-detail.component';
import { FQLeche } from 'app/shared/model/fq-leche.model';

describe('Component Tests', () => {
  describe('FQLeche Management Detail Component', () => {
    let comp: FQLecheDetailComponent;
    let fixture: ComponentFixture<FQLecheDetailComponent>;
    const route = ({ data: of({ fQLeche: new FQLeche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQLecheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FQLecheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FQLecheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fQLeche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fQLeche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
