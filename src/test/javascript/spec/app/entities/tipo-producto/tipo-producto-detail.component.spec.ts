import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { TipoProductoDetailComponent } from 'app/entities/tipo-producto/tipo-producto-detail.component';
import { TipoProducto } from 'app/shared/model/tipo-producto.model';

describe('Component Tests', () => {
  describe('TipoProducto Management Detail Component', () => {
    let comp: TipoProductoDetailComponent;
    let fixture: ComponentFixture<TipoProductoDetailComponent>;
    const route = ({ data: of({ tipoProducto: new TipoProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [TipoProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoProducto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
