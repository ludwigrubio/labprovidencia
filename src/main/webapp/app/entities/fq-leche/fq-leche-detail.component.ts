import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFQLeche } from 'app/shared/model/fq-leche.model';

@Component({
  selector: 'jhi-fq-leche-detail',
  templateUrl: './fq-leche-detail.component.html',
})
export class FQLecheDetailComponent implements OnInit {
  fQLeche: IFQLeche | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQLeche }) => (this.fQLeche = fQLeche));
  }

  previousState(): void {
    window.history.back();
  }
}
