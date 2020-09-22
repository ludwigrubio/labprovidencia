import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { RelacionDetailComponent } from 'app/entities/relacion/relacion-detail.component';
import { Relacion } from 'app/shared/model/relacion.model';

describe('Component Tests', () => {
  describe('Relacion Management Detail Component', () => {
    let comp: RelacionDetailComponent;
    let fixture: ComponentFixture<RelacionDetailComponent>;
    const route = ({ data: of({ relacion: new Relacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [RelacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RelacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load relacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.relacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
