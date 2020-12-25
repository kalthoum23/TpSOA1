import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEmploye, Employe } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { IProjet } from 'app/shared/model/projet.model';
import { ProjetService } from 'app/entities/projet/projet.service';
import { IBureau } from 'app/shared/model/bureau.model';
import { BureauService } from 'app/entities/bureau/bureau.service';

type SelectableEntity = IProjet | IBureau;

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;
  projets: IProjet[] = [];
  bureaus: IBureau[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    projet: [null, Validators.required],
    bureau: [null, Validators.required],
  });

  constructor(
    protected employeService: EmployeService,
    protected projetService: ProjetService,
    protected bureauService: BureauService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      this.updateForm(employe);

      this.projetService.query().subscribe((res: HttpResponse<IProjet[]>) => (this.projets = res.body || []));

      this.bureauService
        .query({ filter: 'employe-is-null' })
        .pipe(
          map((res: HttpResponse<IBureau[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBureau[]) => {
          if (!employe.bureau || !employe.bureau.id) {
            this.bureaus = resBody;
          } else {
            this.bureauService
              .find(employe.bureau.id)
              .pipe(
                map((subRes: HttpResponse<IBureau>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBureau[]) => (this.bureaus = concatRes));
          }
        });
    });
  }

  updateForm(employe: IEmploye): void {
    this.editForm.patchValue({
      id: employe.id,
      nom: employe.nom,
      phone: employe.phone,
      projet: employe.projet,
      bureau: employe.bureau,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employe = this.createFromForm();
    if (employe.id !== undefined) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  private createFromForm(): IEmploye {
    return {
      ...new Employe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      projet: this.editForm.get(['projet'])!.value,
      bureau: this.editForm.get(['bureau'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
