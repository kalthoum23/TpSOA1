import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompetence, Competence } from 'app/shared/model/competence.model';
import { CompetenceService } from './competence.service';

@Component({
  selector: 'jhi-competence-update',
  templateUrl: './competence-update.component.html',
})
export class CompetenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected competenceService: CompetenceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competence }) => {
      this.updateForm(competence);
    });
  }

  updateForm(competence: ICompetence): void {
    this.editForm.patchValue({
      id: competence.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competence = this.createFromForm();
    if (competence.id !== undefined) {
      this.subscribeToSaveResponse(this.competenceService.update(competence));
    } else {
      this.subscribeToSaveResponse(this.competenceService.create(competence));
    }
  }

  private createFromForm(): ICompetence {
    return {
      ...new Competence(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetence>>): void {
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
