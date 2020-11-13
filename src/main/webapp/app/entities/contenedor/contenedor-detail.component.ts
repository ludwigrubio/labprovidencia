import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContenedor } from 'app/shared/model/contenedor.model';

@Component({
  selector: 'jhi-contenedor-detail',
  templateUrl: './contenedor-detail.component.html',
})
export class ContenedorDetailComponent implements OnInit {
  contenedor: IContenedor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenedor }) => (this.contenedor = contenedor));
  }

  previousState(): void {
    window.history.back();
  }
}
