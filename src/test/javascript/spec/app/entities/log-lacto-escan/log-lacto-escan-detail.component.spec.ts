import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabprovidenciaTestModule } from '../../../test.module';
import { LogLactoEscanDetailComponent } from 'app/entities/log-lacto-escan/log-lacto-escan-detail.component';
import { LogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

describe('Component Tests', () => {
  describe('LogLactoEscan Management Detail Component', () => {
    let comp: LogLactoEscanDetailComponent;
    let fixture: ComponentFixture<LogLactoEscanDetailComponent>;
    const route = ({ data: of({ logLactoEscan: new LogLactoEscan(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LabprovidenciaTestModule],
        declarations: [LogLactoEscanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LogLactoEscanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LogLactoEscanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load logLactoEscan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.logLactoEscan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
