import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFQCrema } from 'app/shared/model/fq-crema.model';

@Component({
  selector: 'jhi-fq-crema-detail',
  templateUrl: './fq-crema-detail.component.html',
})
export class FQCremaDetailComponent implements OnInit {
  fQCrema: IFQCrema | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fQCrema }) => {
      this.fQCrema = fQCrema;
      console.error(fQCrema);
    });
  }

  previousState(): void {
    window.history.back();
  }
}
