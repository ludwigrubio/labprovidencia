import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQQuesoDetailComponent } from 'app/entities/fq-queso/fq-queso-detail.component';
import { FQQueso } from 'app/shared/model/fq-queso.model';

describe('Component Tests', () => {
  describe('FQQueso Management Detail Component', () => {
    let comp: FQQuesoDetailComponent;
    let fixture: ComponentFixture<FQQuesoDetailComponent>;
    const route = ({ data: of({ fQQueso: new FQQueso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQQuesoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FQQuesoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FQQuesoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fQQueso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fQQueso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
