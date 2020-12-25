import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ProjetSoaTestModule } from '../../../test.module';
import { BureauComponent } from 'app/entities/bureau/bureau.component';
import { BureauService } from 'app/entities/bureau/bureau.service';
import { Bureau } from 'app/shared/model/bureau.model';

describe('Component Tests', () => {
  describe('Bureau Management Component', () => {
    let comp: BureauComponent;
    let fixture: ComponentFixture<BureauComponent>;
    let service: BureauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProjetSoaTestModule],
        declarations: [BureauComponent],
      })
        .overrideTemplate(BureauComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BureauComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BureauService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bureau(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bureaus && comp.bureaus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
