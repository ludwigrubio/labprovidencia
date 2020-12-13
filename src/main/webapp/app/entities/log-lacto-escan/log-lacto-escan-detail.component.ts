import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogLactoEscan } from 'app/shared/model/log-lacto-escan.model';

@Component({
  selector: 'jhi-log-lacto-escan-detail',
  templateUrl: './log-lacto-escan-detail.component.html',
})
export class LogLactoEscanDetailComponent implements OnInit {
  logLactoEscan: ILogLactoEscan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logLactoEscan }) => (this.logLactoEscan = logLactoEscan));
  }

  previousState(): void {
    window.history.back();
  }
}
