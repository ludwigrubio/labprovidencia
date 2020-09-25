import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { RecepcionDetailComponent } from 'app/entities/recepcion/recepcion-detail.component';
import { Recepcion } from 'app/shared/model/recepcion.model';

describe('Component Tests', () => {
  describe('Recepcion Management Detail Component', () => {
    let comp: RecepcionDetailComponent;
    let fixture: ComponentFixture<RecepcionDetailComponent>;
    const route = ({ data: of({ recepcion: new Recepcion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [RecepcionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RecepcionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecepcionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load recepcion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recepcion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
