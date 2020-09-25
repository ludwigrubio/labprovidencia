import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFQSuero } from 'app/shared/model/fq-suero.model';

@Component({
  selector: 'jhi-fq-suero-detail',
  templateUrl: './fq-suero-detail.component.html',
})
export class FQSueroDetailComponent implements OnInit {
  fQSuero: IFQSuero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQSuero }) => (this.fQSuero = fQSuero));
  }

  previousState(): void {
    window.history.back();
  }
}
