import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFQMantequilla } from 'app/shared/model/fq-mantequilla.model';

@Component({
  selector: 'jhi-fq-mantequilla-detail',
  templateUrl: './fq-mantequilla-detail.component.html',
})
export class FQMantequillaDetailComponent implements OnInit {
  fQMantequilla: IFQMantequilla | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQMantequilla }) => (this.fQMantequilla = fQMantequilla));
  }

  previousState(): void {
    window.history.back();
  }
}
