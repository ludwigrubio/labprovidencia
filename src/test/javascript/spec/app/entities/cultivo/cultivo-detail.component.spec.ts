import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { CultivoDetailComponent } from 'app/entities/cultivo/cultivo-detail.component';
import { Cultivo } from 'app/shared/model/cultivo.model';

describe('Component Tests', () => {
  describe('Cultivo Management Detail Component', () => {
    let comp: CultivoDetailComponent;
    let fixture: ComponentFixture<CultivoDetailComponent>;
    const route = ({ data: of({ cultivo: new Cultivo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [CultivoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CultivoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CultivoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cultivo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cultivo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
