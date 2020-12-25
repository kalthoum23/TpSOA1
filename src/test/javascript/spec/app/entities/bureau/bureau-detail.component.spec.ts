import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetSoaTestModule } from '../../../test.module';
import { BureauDetailComponent } from 'app/entities/bureau/bureau-detail.component';
import { Bureau } from 'app/shared/model/bureau.model';

describe('Component Tests', () => {
  describe('Bureau Management Detail Component', () => {
    let comp: BureauDetailComponent;
    let fixture: ComponentFixture<BureauDetailComponent>;
    const route = ({ data: of({ bureau: new Bureau(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetSoaTestModule],
        declarations: [BureauDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BureauDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BureauDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bureau on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bureau).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
