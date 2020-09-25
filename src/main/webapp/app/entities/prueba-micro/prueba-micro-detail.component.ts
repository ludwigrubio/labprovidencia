import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPruebaMicro } from 'app/shared/model/prueba-micro.model';

@Component({
  selector: 'jhi-prueba-micro-detail',
  templateUrl: './prueba-micro-detail.component.html',
})
export class PruebaMicroDetailComponent implements OnInit {
  pruebaMicro: IPruebaMicro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pruebaMicro }) => (this.pruebaMicro = pruebaMicro));
  }

  previousState(): void {
    window.history.back();
  }
}
