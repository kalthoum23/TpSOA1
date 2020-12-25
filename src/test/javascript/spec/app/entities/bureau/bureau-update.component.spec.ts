import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProjetSoaTestModule } from '../../../test.module';
import { BureauUpdateComponent } from 'app/entities/bureau/bureau-update.component';
import { BureauService } from 'app/entities/bureau/bureau.service';
import { Bureau } from 'app/shared/model/bureau.model';

describe('Component Tests', () => {
  describe('Bureau Management Update Component', () => {
    let comp: BureauUpdateComponent;
    let fixture: ComponentFixture<BureauUpdateComponent>;
    let service: BureauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetSoaTestModule],
        declarations: [BureauUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BureauUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BureauUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BureauService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bureau(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bureau();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
