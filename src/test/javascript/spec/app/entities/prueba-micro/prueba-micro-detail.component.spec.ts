import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { PruebaMicroDetailComponent } from 'app/entities/prueba-micro/prueba-micro-detail.component';
import { PruebaMicro } from 'app/shared/model/prueba-micro.model';

describe('Component Tests', () => {
  describe('PruebaMicro Management Detail Component', () => {
    let comp: PruebaMicroDetailComponent;
    let fixture: ComponentFixture<PruebaMicroDetailComponent>;
    const route = ({ data: of({ pruebaMicro: new PruebaMicro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [PruebaMicroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PruebaMicroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PruebaMicroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pruebaMicro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pruebaMicro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
