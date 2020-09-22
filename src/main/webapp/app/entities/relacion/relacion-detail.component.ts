import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelacion } from 'app/shared/model/relacion.model';

@Component({
  selector: 'jhi-relacion-detail',
  templateUrl: './relacion-detail.component.html',
})
export class RelacionDetailComponent implements OnInit {
  relacion: IRelacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relacion }) => (this.relacion = relacion));
  }

  previousState(): void {
    window.history.back();
  }
}
