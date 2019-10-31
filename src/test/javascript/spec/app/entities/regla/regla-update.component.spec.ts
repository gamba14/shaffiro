/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShaffiroTestModule } from '../../../test.module';
import { ReglaUpdateComponent } from 'app/entities/regla/regla-update.component';
import { ReglaService } from 'app/entities/regla/regla.service';
import { Regla } from 'app/shared/model/regla.model';

describe('Component Tests', () => {
    describe('Regla Management Update Component', () => {
        let comp: ReglaUpdateComponent;
        let fixture: ComponentFixture<ReglaUpdateComponent>;
        let service: ReglaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [ReglaUpdateComponent]
            })
                .overrideTemplate(ReglaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReglaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReglaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Regla(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.regla = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Regla();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.regla = entity;
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
