import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFQQueso } from 'app/shared/model/fq-queso.model';

@Component({
  selector: 'jhi-fq-queso-detail',
  templateUrl: './fq-queso-detail.component.html',
})
export class FQQuesoDetailComponent implements OnInit {
  fQQueso: IFQQueso | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQQueso }) => (this.fQQueso = fQQueso));
  }

  previousState(): void {
    window.history.back();
  }
}
