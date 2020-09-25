import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQCremaDetailComponent } from 'app/entities/fq-crema/fq-crema-detail.component';
import { FQCrema } from 'app/shared/model/fq-crema.model';

describe('Component Tests', () => {
  describe('FQCrema Management Detail Component', () => {
    let comp: FQCremaDetailComponent;
    let fixture: ComponentFixture<FQCremaDetailComponent>;
    const route = ({ data: of({ fQCrema: new FQCrema(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQCremaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FQCremaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FQCremaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fQCrema on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fQCrema).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
