import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from 'app/entities/tipo-producto/tipo-producto.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html',
})
export class ProductoUpdateComponent implements OnInit {
  isSaving = false;
  tipoproductos: ITipoProducto[] = [];

  editForm = this.fb.group({
    id: [],
    producto: [null, [Validators.required, Validators.maxLength(45)]],
    clave: [null, [Validators.required, Validators.maxLength(45)]],
    serieEAN: [null, [Validators.required, Validators.maxLength(45)]],
    tipo: [null, Validators.required],
  });

  constructor(
    protected productoService: ProductoService,
    protected tipoProductoService: TipoProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);

      this.tipoProductoService.query().subscribe((res: HttpResponse<ITipoProducto[]>) => (this.tipoproductos = res.body || []));
    });
  }

  updateForm(producto: IProducto): void {
    this.editForm.patchValue({
      id: producto.id,
      producto: producto.producto,
      clave: producto.clave,
      serieEAN: producto.serieEAN,
      tipo: producto.tipo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id'])!.value,
      producto: this.editForm.get(['producto'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      serieEAN: this.editForm.get(['serieEAN'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITipoProducto): any {
    return item.id;
  }
}
