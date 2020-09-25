import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuperficie } from 'app/shared/model/superficie.model';

@Component({
  selector: 'jhi-superficie-detail',
  templateUrl: './superficie-detail.component.html',
})
export class SuperficieDetailComponent implements OnInit {
  superficie: ISuperficie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ superficie }) => (this.superficie = superficie));
  }

  previousState(): void {
    window.history.back();
  }
}
