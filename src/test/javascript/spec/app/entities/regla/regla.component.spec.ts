/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShaffiroTestModule } from '../../../test.module';
import { ReglaComponent } from 'app/entities/regla/regla.component';
import { ReglaService } from 'app/entities/regla/regla.service';
import { Regla } from 'app/shared/model/regla.model';

describe('Component Tests', () => {
    describe('Regla Management Component', () => {
        let comp: ReglaComponent;
        let fixture: ComponentFixture<ReglaComponent>;
        let service: ReglaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [ReglaComponent],
                providers: []
            })
                .overrideTemplate(ReglaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReglaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReglaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Regla(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.reglas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
