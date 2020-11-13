import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { ContenedorDetailComponent } from 'app/entities/contenedor/contenedor-detail.component';
import { Contenedor } from 'app/shared/model/contenedor.model';

describe('Component Tests', () => {
  describe('Contenedor Management Detail Component', () => {
    let comp: ContenedorDetailComponent;
    let fixture: ComponentFixture<ContenedorDetailComponent>;
    const route = ({ data: of({ contenedor: new Contenedor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [ContenedorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContenedorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContenedorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contenedor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contenedor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
