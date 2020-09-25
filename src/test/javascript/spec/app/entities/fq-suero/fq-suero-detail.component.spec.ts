import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { FQSueroDetailComponent } from 'app/entities/fq-suero/fq-suero-detail.component';
import { FQSuero } from 'app/shared/model/fq-suero.model';

describe('Component Tests', () => {
  describe('FQSuero Management Detail Component', () => {
    let comp: FQSueroDetailComponent;
    let fixture: ComponentFixture<FQSueroDetailComponent>;
    const route = ({ data: of({ fQSuero: new FQSuero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [FQSueroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FQSueroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FQSueroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fQSuero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fQSuero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
