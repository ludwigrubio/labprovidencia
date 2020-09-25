import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICultivo } from 'app/shared/model/cultivo.model';

@Component({
  selector: 'jhi-cultivo-detail',
  templateUrl: './cultivo-detail.component.html',
})
export class CultivoDetailComponent implements OnInit {
  cultivo: ICultivo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cultivo }) => (this.cultivo = cultivo));
  }

  previousState(): void {
    window.history.back();
  }
}
