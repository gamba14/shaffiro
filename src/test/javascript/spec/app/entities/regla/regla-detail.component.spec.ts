/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShaffiroTestModule } from '../../../test.module';
import { ReglaDetailComponent } from 'app/entities/regla/regla-detail.component';
import { Regla } from 'app/shared/model/regla.model';

describe('Component Tests', () => {
    describe('Regla Management Detail Component', () => {
        let comp: ReglaDetailComponent;
        let fixture: ComponentFixture<ReglaDetailComponent>;
        const route = ({ data: of({ regla: new Regla(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [ReglaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReglaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReglaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regla).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
