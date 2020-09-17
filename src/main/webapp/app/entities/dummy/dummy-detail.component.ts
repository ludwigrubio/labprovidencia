import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDummy } from 'app/shared/model/dummy.model';

@Component({
  selector: 'jhi-dummy-detail',
  templateUrl: './dummy-detail.component.html',
})
export class DummyDetailComponent implements OnInit {
  dummy: IDummy | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dummy }) => (this.dummy = dummy));
  }

  previousState(): void {
    window.history.back();
  }
}
