import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { SuperficieDetailComponent } from 'app/entities/superficie/superficie-detail.component';
import { Superficie } from 'app/shared/model/superficie.model';

describe('Component Tests', () => {
  describe('Superficie Management Detail Component', () => {
    let comp: SuperficieDetailComponent;
    let fixture: ComponentFixture<SuperficieDetailComponent>;
    const route = ({ data: of({ superficie: new Superficie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [SuperficieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SuperficieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuperficieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load superficie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.superficie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
