import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBureau, Bureau } from 'app/shared/model/bureau.model';
import { BureauService } from './bureau.service';

@Component({
  selector: 'jhi-bureau-update',
  templateUrl: './bureau-update.component.html',
})
export class BureauUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
  });

  constructor(protected bureauService: BureauService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bureau }) => {
      this.updateForm(bureau);
    });
  }

  updateForm(bureau: IBureau): void {
    this.editForm.patchValue({
      id: bureau.id,
      numero: bureau.numero,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bureau = this.createFromForm();
    if (bureau.id !== undefined) {
      this.subscribeToSaveResponse(this.bureauService.update(bureau));
    } else {
      this.subscribeToSaveResponse(this.bureauService.create(bureau));
    }
  }

  private createFromForm(): IBureau {
    return {
      ...new Bureau(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBureau>>): void {
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
}
